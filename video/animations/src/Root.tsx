import "./index.css";
import { Composition } from "remotion";
import { Intro } from "./Intro";
import { BigSmall } from "./BigSmall";
import { AiEdit } from "./AiEdit";
import { Outro } from "./Outro";

const FPS = 60;
const WIDTH = 1080;
const HEIGHT = 1920

export const RemotionRoot: React.FC = () => {
  return (
    <>
      <Composition
        id="Intro"
        component={Intro}
        durationInFrames={6 * FPS}
        fps={FPS}
        width={WIDTH}
        height={HEIGHT}
      />
      
      <Composition
        id="BigSmall"
        component={BigSmall}
        durationInFrames={4 * FPS}
        fps={FPS}
        width={WIDTH}
        height={HEIGHT}
      />
      
      <Composition
        id="AiEdit"
        component={AiEdit}
        durationInFrames={3 * FPS}
        fps={FPS}
        width={WIDTH}
        height={HEIGHT}
      />
      
      <Composition
        id="Outro"
        component={Outro}
        durationInFrames={5 * FPS}
        fps={FPS}
        width={WIDTH}
        height={HEIGHT}
      />
    </>
  );
};
