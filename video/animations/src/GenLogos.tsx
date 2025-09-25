import {AbsoluteFill, Sequence, useCurrentFrame, interpolate, interpolateColors, Easing, Img, staticFile} from 'remotion';
import { z } from "zod";
import { zColor } from "@remotion/zod-types";
import { loadFont as loadRobotoSlabFont } from '@remotion/google-fonts/RobotoSlab';

const ROBOTO_SLAB_FONT = loadRobotoSlabFont();

const MOVE_START = 40;
const MOVE_END = 320;

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

export const ImageBox: React.FC = (props) => {
  return (
    <div style={{
      position: 'relative',
      display: 'inline-block',
      width: '600px',
      height: '450px',
      paddingTop: '50px',
      textAlign: 'center'
    }}>
      <Img src={staticFile(props.src)} style={{
        display: 'inline-block',
        height: '300px',
        width: 'auto',
      }} />
    </div>
  );
}
  
export const GenLogos: React.FC = () => {
  const frame = useCurrentFrame();
  
  const left = i(frame, [MOVE_START, MOVE_END], [0, -1900], EASE_IN_OUT);
  
  return (
    <AbsoluteFill style={{backgroundColor: '#EEEEEE'}}>
      <div style={{
        position: 'absolute',
        top: '70px',
        left: left + 'px',
        fontSize: '100px',
        whiteSpace: 'nowrap',
      }}>
        <ImageBox src="img/gen/mostly-ai.svg" />
        <ImageBox src="img/gen/tonic.svg" />
        <ImageBox src="img/gen/genrocket.svg" />
        <ImageBox src="img/gen/delphix.svg" />
        <ImageBox src="img/gen/testrail.svg" />
        <br />
        <ImageBox src="img/gen/flyway.svg" />
        <ImageBox src="img/gen/liquibase.svg" />
        <ImageBox src="img/gen/laravel-seeder.svg" />
        <ImageBox src="img/gen/sequelize.svg" />
        <ImageBox src="img/gen/alembic.svg" />
        <br />
        <ImageBox src="img/gen/faker.svg" />
        <ImageBox src="img/gen/fakerjs.svg" />
        <ImageBox src="img/gen/pydbgen.svg" />
        <ImageBox src="img/gen/mimesis.svg" />
        <ImageBox src="img/gen/synthcity.svg" />
        <br />
        <ImageBox src="img/gen/postman.svg" />
        <ImageBox src="img/gen/bruno.svg" />
        <ImageBox src="img/gen/json-placeholder.svg" />
        <ImageBox src="img/gen/mockaroo.svg" />
        <ImageBox src="img/gen/ca-tdm.svg" />
      </div>
    </AbsoluteFill>
  );
};
