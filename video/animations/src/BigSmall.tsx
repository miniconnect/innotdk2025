import {AbsoluteFill, Sequence, useCurrentFrame, interpolate, interpolateColors, Easing, Img, staticFile} from 'remotion';
import { z } from "zod";
import { zColor } from "@remotion/zod-types";
import { loadFont as loadRobotoSlabFont } from '@remotion/google-fonts/RobotoSlab';

const ROBOTO_SLAB_FONT = loadRobotoSlabFont();

const SCALE_START = 12;
const SCALE_END = 180;
const PLANE_START = 80;
const PLANE_END = 240;

const EASE_IN_OUT =  {
  easing: Easing.inOut(Easing.ease),
    extrapolateRight: 'clamp',
};

const i = (t, ts, vs, ps) => {
  return interpolate(t, [-1, ...ts, 1000], [vs[0], ...vs, vs[vs.length - 1]], ps);
}

const ic = (t, ts, vs, ps) => {
  return interpolateColors(t, [-1, ...ts, 1000], [vs[0], ...vs, vs[vs.length - 1]], ps);
}

export const BigSmall: React.FC = () => {
  const frame = useCurrentFrame();
  
  const scale = i(frame, [SCALE_START, SCALE_END], [1, 4.4], EASE_IN_OUT);
  const rotation = i(frame, [PLANE_START, PLANE_END], [50, -30], EASE_IN_OUT);
  
  return (
    <AbsoluteFill style={{backgroundColor: '#DDEEFF'}}>
      <div style={{
        transform: 'scale(' + scale + ')',
        transformOrigin: '1000px 1750px',
      }}>
        <Img src={staticFile("img/big-small.svg")} width="1080" />
      </div>
      <div style={{
        position: 'absolute',
        top: '900px',
        left: '500px',
        transform: 'rotate(' + rotation + 'deg)',
        transformOrigin: '-200px -1700px',
      }}>
        <Img src={staticFile("img/paper-plane.svg")} width="320" />
      </div>
    </AbsoluteFill>
  );
};
