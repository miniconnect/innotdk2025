import "./index.css";
import { Composition } from "remotion";
import { Intro, introSchema } from "./Intro";

export const RemotionRoot: React.FC = () => {
  return (
    <>
      <Composition
        id="Intro"
        component={Intro}
        durationInFrames={150}
        fps={30}
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
    </>
  );
};
