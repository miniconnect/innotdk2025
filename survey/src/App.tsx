import React from 'react';
import { Routes, Route } from 'react-router-dom'
import './App.css';

import HomePage from './HomePage';
import HolodbSurvey from './HolodbSurvey';
import ThanksPage from './ThanksPage';

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/survey" element={<HolodbSurvey />} />
        <Route path="/thanks" element={<ThanksPage/>} />
        <Route path="*" element={<HomePage />} />
      </Routes>
    </div>
  );
}

export default App;
