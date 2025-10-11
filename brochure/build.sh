#!/bin/sh

selfDir="$( dirname -- "$( realpath -- "$0" )" )"
pagesDir="${selfDir}/pages"
buildDir="${selfDir}/build"
outDir="${selfDir}/out"

rm -rf "$buildDir"
mkdir -p "$buildDir"

for svgPath in "${pagesDir}/"*svg; do
    pageName="$( basename "$svgPath" | sed -E 's/\.svg$//' )"
    pdfPath="${buildDir}/${pageName}-raw.pdf"
    inkscape "$svgPath" --export-type=pdf --export-filename="${pdfPath}" --export-text-to-path --export-dpi=300
done

gs -dSAFER -dBATCH -dNOPAUSE \
   -sDEVICE=pdfwrite \
   -dCompatibilityLevel=1.6 \
   -dPDFX=true \
   -dPDFSETTINGS=/prepress \
   -dProcessColorModel=/DeviceCMYK \
   -sColorConversionStrategy=CMYK \
   -dKeepPDFShadings=true \
   -dPreserveOverprintSettings=true \
   -dPreserveSeparation=true \
   -sOutputICCProfile='/usr/share/color/icc/manual/ISOcoated_v2_eci.icc' \
   -sOutputFile="${buildDir}/press.pdf" \
   "${buildDir}/"*'-raw.pdf' \
;

python3 - <<'PY' "${buildDir}/press.pdf" "${buildDir}/final.pdf" 3
import sys
from pikepdf import Pdf, Array

infile, outfile, margin_mm = sys.argv[1], sys.argv[2], float(sys.argv[3])
mm2pt = lambda mm: mm * 72.0 / 25.4
m = mm2pt(margin_mm)

pdf = Pdf.open(infile)
for page in pdf.pages:
    x0, y0, x1, y1 = map(float, page.obj.get('/MediaBox'))
    # Bleed = full page
    page.obj['/BleedBox'] = Array([x0, y0, x1, y1])
    # Trim inset
    page.obj['/TrimBox']  = Array([x0+m, y0+m, x1-m, y1-m])
    # Optional: make viewers show the trimmed view
    page.obj['/CropBox']  = page.obj['/TrimBox']
pdf.save(outfile)
PY

rm -rf "$outDir"
mkdir -p "$outDir"

cp "${buildDir}/final.pdf" "${outDir}/final.pdf"
