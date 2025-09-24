import "./index.css";
import { Composition } from "remotion";
import { Intro, introSchema } from "./Intro";
import { Outro, outroSchema } from "./Outro";

const FPS = 60;

export const RemotionRoot: React.FC = () => {
  return (
    <>
      <Composition
        id="Intro"
        component={Intro}
        durationInFrames={6 * FPS}
        fps={FPS}
        width={1080}
        height={1920}
        schema={introSchema}
        defaultProps={{
          titleText: "Intro",
          titleColor: "#000000",
          logoColor1: "#91EAE4",
          logoColor2: "#86A8E7",
        }}
      />
      
      <Composition
        id="Outro"
        component={Outro}
        durationInFrames={5 * FPS}
        fps={FPS}
        width={1080}
        height={1920}
        schema={outroSchema}
        defaultProps={{
          titleText: "Intro",
          titleColor: "#000000",
          logoColor1: "#91EAE4",
          logoColor2: "#86A8E7",
        }}
      />
    </>
  );
};
