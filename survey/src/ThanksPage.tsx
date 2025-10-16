import { useNavigate  } from 'react-router-dom';
import { useCallback, useEffect, useRef } from 'react';

const ThanksPage = () => {
  const timerRef = useRef<number | null>(null);
  let navigate = useNavigate();
  
  useEffect(() => {
    if (timerRef.current !== null) {
      return;
    }
    timerRef.current = window.setTimeout(() => {
      timerRef.current = null;
      navigate("/");
    }, 1500000);
  }, []);
    
  const backNow = useCallback(() => {
    console.log(timerRef)
    if (timerRef.current !== null) {
      clearTimeout(timerRef.current);
      timerRef.current = null;
    }
    navigate("/");
  }, []);

  return <div>
    <div className="thanks-page">
      <p className="thanks-page-text">Köszönöm a kitöltést!</p>
      <div className="thanks-page-link-outer">
        <button className="thanks-page-link" type="button" onClick={backNow}>
          Újra
        </button>
      </div>
    </div>
  </div>
}
export default ThanksPage;
