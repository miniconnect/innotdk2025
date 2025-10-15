import { NavLink } from 'react-router-dom';
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
    }, 30000);
    console.log("STARTED: " + timerRef.current)
  }, []);
    
  const backNow = useCallback(() => {
    console.log(timerRef)
    if (timerRef.current !== null) {
      console.log("CLEAR")
      clearTimeout(timerRef.current);
      timerRef.current = null;
    }
    navigate("/");
  }, []);

  return <div>
    <p>THANKS</p>
    <p><button onClick={backNow}>BACK TO HOME</button></p>
  </div>
}
export default ThanksPage;
