#!/bin/sh

selfDir="$( dirname -- "$( realpath -- "$0" )" )"
buildDir="${selfDir}/build"

rm -rf "$buildDir"
mkdir -p "$buildDir"

inkscape "${selfDir}/poster.svg" --export-type=pdf --export-filename="${buildDir}/poster_raw.pdf" --export-dpi=300

gs -dSAFER -dBATCH -dNOPAUSE -sDEVICE=pdfwrite -dPDFSETTINGS=/prepress -dProcessColorModel=/DeviceCMYK -sColorConversionStrategy=CMYK -sOutputFile="${buildDir}/poster_press.pdf" "${buildDir}/poster_raw.pdf"

python3 - <<'PY' "${buildDir}/poster_press.pdf" "${buildDir}/poster.pdf" 5
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

rm "${buildDir}/poster_"*
