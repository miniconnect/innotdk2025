import {AbsoluteFill, Sequence, useCurrentFrame, interpolate, interpolateColors, Easing, Img, staticFile} from 'remotion';
import { z } from "zod";
import { zColor } from "@remotion/zod-types";
import { loadFont as loadRobotoSlabFont } from '@remotion/google-fonts/RobotoSlab';

const ROBOTO_SLAB_FONT = loadRobotoSlabFont();

const KF_QR_IN_START = 0;
const KF_QR_IN_END = 30;
const KF_QR_OUT_START = 230;
const KF_QR_OUT_END = 260;
const KF_FINAL_IN_START = 260;
const KF_FINAL_IN_END = 290;

const i = (t, ts, vs, ps) => {
  return interpolate(t, [-1, ...ts, 1000], [vs[0], ...vs, vs[vs.length - 1]], ps);
}

const ic = (t, ts, vs, ps) => {
  return interpolateColors(t, [-1, ...ts, 1000], [vs[0], ...vs, vs[vs.length - 1]], ps);
}

export const Outro: React.FC = () => {
  const frame = useCurrentFrame();
  
  const qrOpacity = i(frame, [KF_QR_IN_START, KF_QR_IN_END, KF_QR_OUT_START, KF_QR_OUT_END], [0, 1, 1, 0]);
  const finalOpacity = i(frame, [KF_FINAL_IN_START, KF_FINAL_IN_END], [0, 1]);
  
  return (
    <AbsoluteFill style={{backgroundColor: 'black'}}>
      <Sequence name="QR" from={KF_QR_IN_START} durationInFrames={KF_QR_OUT_END - KF_QR_IN_START}>
        <AbsoluteFill style={{backgroundColor: 'black',opacity:qrOpacity}}>
          <div style={{
            display: 'block',
            marginTop: '270px',
            textAlign: 'center',
          }}>
            <div style={{
              display: 'inline-block',
              backgroundColor: 'white',
              padding: '30px',
              borderRadius: '30px',
            }}>
              <Img src={staticFile("img/holodb-qr.svg")} width="750" />
            </div>
          </div>
          <div style={{
            display: 'block',
            marginTop: '150px',
            fontSize: '87px',
            color: 'white',
            textAlign: 'center',
            fontFamily: ROBOTO_SLAB_FONT.fontFamily,
          }}>
              <span style={{color:'#D9D9D9',fontSize:'94%'}}>https://github.com/</span><br />miniconnect/holodb
          </div>
        </AbsoluteFill>
      </Sequence>
      <Sequence name="Final" from={KF_FINAL_IN_START}>
        <AbsoluteFill style={{
          backgroundColor: 'black',
          opacity: finalOpacity,
        }}>
          <Img src={staticFile("img/final.svg")} width="1080" />
        </AbsoluteFill>
      </Sequence>
    </AbsoluteFill>
  );
};
