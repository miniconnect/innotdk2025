import 'survey-core/survey-core.css';
import { useCallback, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Model } from 'survey-core';
import { Survey } from 'survey-react-ui';

const surveyJson = {
  title: "My survey",
  firstPageIsStartPage: false,
  pages: [
    {
      elements: [{
        type: "radiogroup",
        name: "terulet",
        title: "Milyen jellegű területen dolgozol?",
        choices: [
          { value: "fejlesztes", text: "szoftverfejlesztésben dolgozom" },
          { value: "kapcsolodo", text: "egyéb, szoftverfejlesztéshez kapcsolódó területen dolgozom" },
          { value: "adatokkal", text: "egyéb területen, adatokkal dolgozom" },
          { value: "egyeb", text: "egyéb" },
        ],
      }],
    },
    {
      elements: [{
        type: "radiogroup",
        visibleIf: "{terulet} anyof ['fejlesztes', 'kapcsolodo', 'adatokkal']",
        name: "munkakor",
        title: "Milyen jellegű munkakörben dolgozol?",
        choices: [
          { value: "fejleszto", text: "fejlesztő vagyok" },
          { value: "tesztelo", text: "tesztelő vagyok" },
          { value: "kutato", text: "kutató vagyok" },
          { value: "csapatvezeto", text: "csapatvezető vagyok" },
          { value: "nagyfonok", text: "magasabb szintű vezető vagyok" },
          { value: "egyeb", text: "egyéb" },
        ],
      }],
    },
    {
      elements: [{
        type: "radiogroup",
        name: "jellemzo",
        title: "Melyiket érzed a legjellemzőbbnek magadra az alábbiak közül?",
        description: "(amikor van választási lehetőséged)",
        choices: [
          { value: "fizetos", text: "általában a leggördülékenyebben használható, akár fizetős megoldást választom" },
          { value: "online", text: "leginkább az online ingyenesen elérhető eszközöket használom (mindenre van már egy weboldal)" },
          { value: "robusztus-nyilt", text: "preferálom a nyílt forrású szoftvert, de csak ha elég robusztus" },
          { value: "csak-nyilt", text: "csak nyílt forrású szoftvert vagyok hajlandó használni" },
          { value: "manualis", text: "szeretem saját kézzel létrehozni az eszköztáramat (például szkriptek, makrók, testreszabott felhasználói felület stb.)" },
          { value: "mas", text: "egyik sem igazán" },
        ],
      }],
    },
    {
      elements: [{
        type: "radiogroup",
        name: "milyen-gyakran",
        title: "Munkád során milyen gyakran van szükséged adatokra anélkül, hogy azoknak feltétlenül valós forrásból kellene származniuk?",
        description: "Gondolhatsz bármilyen helyzetre, nem kell fejlesztési projektnek lennie.",
        choices: [
          { value: "naponta", text: "naponta" },
          { value: "gyakran", text: "viszonylag gyakran" },
          { value: "idonkent", text: "időnként" },
          { value: "ritkan", text: "ritkán" },
          { value: "soha", text: "nem vagy szinte soha" },
        ],
      }],
    },
    {
      elements: [{
        type: "comment",
        name: "peldak",
        title: "Röviden tudnál írni egy-két jellemző példát, milyen esetben fordul ez elő?",
        rows: 2,
        autoGrow: true,
        allowResize: false
      }],
    },
    {
      elements: [{
        type: "comment",
        name: "eloallitas",
        title: "Általában hogyan oldod meg az adatok előállítását?",
        rows: 2,
        autoGrow: true,
        allowResize: false
      }],
    },
    {
      elements: [{
        type: "checkbox",
        name: "eszkozok",
        title: "Mely adatgeneráláshoz kötődő eszközöket használtad már az alábbiak közül?",
        choices: [
          { value: "faker", text: "Faker (JS, PHP, Python, stb.)" },
          { value: "laravel", text: "Laravel seeder" },
          { value: "mockaroo", text: "Mockaroo" },
          { value: "mostlyai", text: "MostlyAI" },
          { value: "tonic", text: "Tonic" },
          { value: "delphix", text: "Delphix" },
          { value: "genrocket", text: "GenRocket" },
        ],
      }],
    },
    {
      elements: [{
        type: "comment",
        name: "egyeb-eszközök",
        title: "Milyen egyéb eszközöket tartasz érdemesnek megemlíteni?",
        rows: 2,
        autoGrow: true,
        allowResize: false
      }],
    },
    {
      elements: [{
        type: "comment",
        name: "altalanos-elegedettseg",
        title: "Mennyire vagy elégedett ezekkel az eszközökkel?",
        rows: 2,
        autoGrow: true,
        allowResize: false
      }],
    },
    {
      elements: [{
        type: "rating",
        name: "altalanos-elegedettseg",
        title: "Összességében mennyire érzed megoldottnak az adatgenerálást?",
        rateValues: [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ]
      }],
    },
    {
      elements: [{
        type: "radiogroup",
        name: "holodb-kiprobalva",
        title: "Kipróbáltad már a HoloDB-t bármilyen formában?",
        description: "(akár csak a demókon keresztül)",
        choices: [
          { value: "alaposan", text: "már alaposabban kipróbáltam" },
          { value: "valamennyire", text: "már valamennyire kipróbáltam" },
          { value: "van-rola-kepe", text: "nem próbáltam ki, de már van róla képem (dokumentációból, perzentáció vagy brossúra alapján stb.)" },
          { value: "nem", text: "még nem próbáltam ki" },
        ],
      }],
    },
    {
      elements: [{
        type: "comment",
        name: "benyomas",
        title: "Röviden összefoglalva, milyen benyomást tedd rád a HoloDB, illetve annak megközelítése?",
        rows: 2,
        autoGrow: true,
        allowResize: false
      }],
    },
    {
      elements: [{
        type: "radiogroup",
        name: "hasznalna",
        title: "Mit gondolsz, használnád a gyakorlatban?",
        choices: [
          { value: "hasznalta", text: "már használtam is a gyakorlatban" },
          { value: "szivesen", text: "igen, szívesen használnám" },
          { value: "lehetseges", text: "lehetséges, hogy használnám" },
          { value: "nem-fogja", text: "nem hiszem, hogy használni fogom, van jobb megoldásom" },
          { value: "nem-relevans", text: "nem releváns vagy nem tudom megítélni" },
        ],
      }],
    },
    {
      elements: [{
        type: "checkbox",
        name: "nyitottsag",
        title: "Mire lennél nyitott az alábbiak közül?",
        choices: [
          { value: "fejlesztes", text: "szívesen csatlakoznék fejlesztőként" },
          { value: "support", text: "szívesen kontributálnék egyéb módon a projektbe (például dokumentáció, tesztelés, terjesztés stb.)" },
          { value: "tamogatas", text: "akár támogatnám a projektet adománnyal, befektetéssel vagy adoptálással" },
          { value: "publikacio", text: "el tudom képzelni, hogy a HoloDB-vel kapcsolatos szakdolgozatot, publikációt stb. írjak" },
          { value: "egyeb", text: "egyéb együttműködési ötletem van" },
        ],
      }],
    },
    {
      elements: [{
        type: "multipletext",
        name: "adatok",
        visibleIf: "{nyitottsag} anyof ['fejlesztes', 'support', 'tamogatas', 'publikacio', 'egyeb']",
        title: "Megígértük, hogy a kérdőív anonim lesz. Az előző kérdésre adott válaszod alapján viszont érdemes lehet felvennünk a kapcsolatot. Most lehetőséged van megadni egy-két adatot magadról:",
        description: "(természetesen nem kötelező)",
        items: [
        {
          name: "nev",
          title: "Név:",
        },
        {
          name: "email",
          title: " Email:",
          inputType: "email"
        },
        {
          name: "magamrol",
          title: "Magamról egy mondatban:",
        },
        {
          name: "miert",
          title: "Amiért érdekel a projekt:",
        },
      ]
      }],
    },
  ],
}

const HolodbSurvey = () => {
  const [survey] = useState(() => new Model(surveyJson));
  let navigate = useNavigate();
  
  const surveyComplete = useCallback((survey: Model) => {
    survey.setValue("testfield", "lorem"); // FIXME
    fetch('/api.php', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(survey.data)
    });
    navigate("/thanks");
  }, []);

  survey.onComplete.add(surveyComplete);
  
  return <Survey model={survey} />;
}
export default HolodbSurvey;
