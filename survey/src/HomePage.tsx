import { NavLink } from 'react-router-dom';

const HomePage = () => {
  return <div>
    <p>HOME</p>
    <p><NavLink to="/survey" end>TO SURVEY</NavLink></p>
  </div>
}
export default HomePage;
