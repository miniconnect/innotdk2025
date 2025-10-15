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
        name: "FirstName",
        title: "Enter your first name:",
        type: "text"
      }, {
        name: "LastName",
        title: "Enter your last name:",
        type: "text"
      }]
    }
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
