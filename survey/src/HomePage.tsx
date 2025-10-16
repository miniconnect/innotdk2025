import { NavLink } from 'react-router-dom';

const HomePage = () => {
  return <div className="home-page">
    <div className="home-page-logo"></div>
    <h1 className="home-page-title">Véleményfelmérő kérdőív</h1>
    <p className="home-page-text">
      A&nbsp;kérdőívben preferenciádra, véleményedre kérdezünk rá adatokkal való munkával kapcsolatban.
      A&nbsp;kitöltés csak néhány percet vesz igénybe.
      A&nbsp;kérdőív anonim, de a végén lehetőséged van kapcsolatfelvételre.
    </p>
    <p className="home-page-link-outer"><NavLink to="/survey" className="home-page-link" end>Indítás</NavLink></p>
  </div>
}
export default HomePage;
