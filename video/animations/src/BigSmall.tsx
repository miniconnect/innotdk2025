import {AbsoluteFill, Sequence, useCurrentFrame, interpolate, interpolateColors, Easing, Img, staticFile} from 'remotion';
import { z } from "zod";
import { zColor } from "@remotion/zod-types";
import { loadFont as loadRobotoSlabFont } from '@remotion/google-fonts/RobotoSlab';

const ROBOTO_SLAB_FONT = loadRobotoSlabFont();

const KF_QR_IN_START = 0;
const KF_QR_IN_END = 30;
const KF_QR_OUT_START = 100;
const KF_QR_OUT_END = 130;
const KF_FINAL_IN_START = 130;
const KF_FINAL_IN_END = 160;

const i = (t, ts, vs, ps) => {
  return interpolate(t, [-1, ...ts, 1000], [vs[0], ...vs, vs[vs.length - 1]], ps);
}

const ic = (t, ts, vs, ps) => {
  return interpolateColors(t, [-1, ...ts, 1000], [vs[0], ...vs, vs[vs.length - 1]], ps);
}

export const BigSmall: React.FC = () => {
  const frame = useCurrentFrame();
  
  const qrOpacity = i(frame, [KF_QR_IN_START, KF_QR_IN_END, KF_QR_OUT_START, KF_QR_OUT_END], [0, 1, 1, 0]);
  const finalOpacity = i(frame, [KF_FINAL_IN_START, KF_FINAL_IN_END], [0, 1]);
  
  return (
    <AbsoluteFill style={{backgroundColor: 'white'}}>
      <p style={{fontSize:'100px'}}>Hello!</p>
    </AbsoluteFill>
  );
};
