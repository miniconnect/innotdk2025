import {AbsoluteFill, Sequence, useCurrentFrame, interpolate, interpolateColors, Easing, Img, staticFile} from 'remotion';
import { z } from "zod";
import { zColor } from "@remotion/zod-types";
import { loadFont as loadRobotoMonoFont } from '@remotion/google-fonts/RobotoMono';
import { loadFont as loadRobotoSlabFont } from '@remotion/google-fonts/RobotoSlab';

const ROBOTO_MONO_FONT = loadRobotoMonoFont();
const ROBOTO_SLAB_FONT = loadRobotoSlabFont();

const BACKGROUND_COLOR = '#221133';
const YAML_OPERATOR_COLOR = '#EEEEEE';
const YAML_KEY_COLOR = '#FFDDCC';
const YAML_NUMBER_COLOR = '#FF9999';
const YAML_STRING_COLOR = '#CCCCBB';
const NEW_CODE_COLOR = '#77AA55';

const PROMPT_START = 20;
const PROMPT_END = 50;
const INSERT_START = 100;
const INSERT_END = 160;

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

const YamlKey: React.FC = (props) => {
  return <span style={{color:YAML_KEY_COLOR}}>{props.children}</span>
};

const YamlNumber: React.FC = (props) => {
  return <span style={{color:YAML_NUMBER_COLOR}}>{props.children}</span>
};

const YamlString: React.FC = (props) => {
  return <span style={{color:YAML_STRING_COLOR}}>{props.children}</span>
};

export const AiEdit: React.FC = () => {
  const frame = useCurrentFrame();
  
  const promptLeft = i(frame, [PROMPT_START, PROMPT_END], [-400, 80], EASE_IN_OUT);
  const promptOpacity = i(frame, [PROMPT_START, PROMPT_END], [0, 1], EASE_IN_OUT);
  const lowerTextTop = i(frame, [INSERT_START, INSERT_END], [835, 1020], EASE_IN_OUT);
  const handRotation = i(frame, [INSERT_START, INSERT_END], [-45, -5], EASE_IN_OUT);
  const insertTextTop = i(frame, [INSERT_START, INSERT_END], [880, 835], EASE_IN_OUT);
  const insertTextLeft = i(frame, [INSERT_START, INSERT_END], [500, 208], EASE_IN_OUT);
  const insertOpacity = i(frame, [INSERT_START, INSERT_END], [0, 1], EASE_IN_OUT);
  const leftEyeLeft = i(frame, [INSERT_START, INSERT_END], [797, 782], EASE_IN_OUT);
  const rightEyeLeft = i(frame, [INSERT_START, INSERT_END], [932, 917], EASE_IN_OUT);
  
  return (
    <AbsoluteFill style={{backgroundColor:BACKGROUND_COLOR}}>
      
      <div style={{
        position: 'absolute',
        top: '50px',
        left: '20px',
        width: '1200px',
        fontFamily: ROBOTO_MONO_FONT.fontFamily,
        fontSize: '40px',
        color: YAML_OPERATOR_COLOR,
      }}>
        <div><YamlKey>seed</YamlKey>: <YamlNumber>2346237</YamlNumber></div>
        <div><YamlKey>schemas</YamlKey>:</div>
        <div>&nbsp;&nbsp;- <YamlKey>name</YamlKey>: <YamlString>economy</YamlString></div>
        <div>&nbsp;&nbsp;&nbsp;&nbsp;<YamlKey>tables</YamlKey>:</div>
        <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- <YamlKey>name</YamlKey>: <YamlString>employees</YamlString></div>
        <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<YamlKey>size</YamlKey>: <YamlNumber>1200</YamlNumber></div>
        <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<YamlKey>columns</YamlKey>:</div>
        <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- <YamlKey>name</YamlKey>: <YamlString>id</YamlString></div>
        <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<YamlKey>mode</YamlKey>: <YamlString>counter</YamlString></div>
        <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- <YamlKey>name</YamlKey>: <YamlString>firstname</YamlString></div>
        <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<YamlKey>valuesBundle</YamlKey>: <YamlString>forenames</YamlString></div>
        <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- <YamlKey>name</YamlKey>: <YamlString>lastname</YamlString></div>
        <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<YamlKey>valuesBundle</YamlKey>: <YamlString>surnames</YamlString></div>
      </div>
      <div style={{
        position: 'absolute',
        top: lowerTextTop + 'px',
        left: '20px',
        width: '1200px',
        fontFamily: ROBOTO_MONO_FONT.fontFamily,
        fontSize: '40px',
        color: YAML_OPERATOR_COLOR,
      }}>
        <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- <YamlKey>name</YamlKey>: <YamlString>birth_year</YamlString></div>
        <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<YamlKey>valuesRange</YamlKey>: [<YamlNumber>1950</YamlNumber>, <YamlNumber>2005</YamlNumber>]</div>
        <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- <YamlKey>name</YamlKey>: <YamlString>companies</YamlString></div>
        <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<YamlKey>size</YamlKey>: <YamlNumber>45</YamlNumber></div>
        <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<YamlKey>columns</YamlKey>:</div>
        <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- <YamlKey>name</YamlKey>: <YamlString>id</YamlString></div>
        <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<YamlKey>mode</YamlKey>: <YamlString>counter</YamlString></div>
      </div>
      
      
      <div style={{
        position: 'absolute',
        top: insertTextTop + 'px',
        left: insertTextLeft + 'px',
        opacity: insertOpacity,
      }}>
        <div style={{
          position: 'absolute',
          top: '10px',
          left: '0px',
          width: '80px',
          fontFamily: ROBOTO_MONO_FONT.fontFamily,
          fontSize: '100px',
          fontWeight: 'bold',
          color: NEW_CODE_COLOR,
        }}>
          +
        </div>
        <div style={{
          position: 'absolute',
          top: '0px',
          left: '100px',
          width: '900px',
          backgroundColor: BACKGROUND_COLOR,
          fontFamily: ROBOTO_MONO_FONT.fontFamily,
          fontSize: '40px',
          fontWeight: 'bold',
          color: NEW_CODE_COLOR,
        }}>
          <div>- name: contact_phone</div>
          <div>&nbsp;&nbsp;valuesPattern: '\+1 \d{3}-\d{3}-\d{4}'</div>
          <div>&nbsp;&nbsp;nullCount: 42</div>
        </div>
      </div>
      
      
      <div>
        <Img src={staticFile("img/ai.svg")} width="450" style={{
          position: 'absolute',
          top: '330px',
          left: '650px',
        opacity: insertOpacity,
        }} />
      </div>
      <Img src={staticFile("img/ai-hand.svg")} width="250" style={{
        display: 'block',
        position: 'absolute',
        top: '600px',
        left: '600px',
        opacity: insertOpacity,
        transformOrigin: '240px 10px',
        transform: 'rotate(' + handRotation + 'deg)',
      }} />
      <Img src={staticFile("img/ai-hand.svg")} width="250" style={{
        display: 'block',
        position: 'absolute',
        top: '600px',
        left: '750px',
        opacity: insertOpacity,
        transformOrigin: '240px 10px',
        transform: 'rotate(' + handRotation + 'deg)',
      }} />
      <div style={{
        position: 'absolute',
        top: '442px',
        left: leftEyeLeft + 'px',
        opacity: insertOpacity,
        width: '30px',
        height: '30px',
        borderRadius: '15px',
        backgroundColor: '#6633CC',
      }}>
      </div>
      <div style={{
        position: 'absolute',
        top: '442px',
        left: rightEyeLeft + 'px',
        opacity: insertOpacity,
        width: '30px',
        height: '30px',
        borderRadius: '15px',
        backgroundColor: '#6633CC',
      }}>
      </div>
      
      
      <div style={{
        position: 'absolute',
        top: '1560px',
        left: promptLeft + 'px',
        opacity: promptOpacity,
        whiteSpace: 'nowrap',
        padding: '50px 25px',
        border: '10px solid #332211',
        borderRadius: '70px',
        borderBottomLeftRadius: '0px',
        backgroundColor: '#FFFAEE',
        fontFamily: ROBOTO_SLAB_FONT.fontFamily,
        fontSize: '52px',
        color: '#332211',
      }}>
        Az ügyfélnek lehessen mobilszáma!
      </div>
      
    </AbsoluteFill>
  );
};
