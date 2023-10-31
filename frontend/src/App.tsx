import React from 'react';
import './App.css';
import { Routes, Route } from 'react-router-dom';
import Main from './pages/Main';
import Login from './pages/Login';
import Room from './pages/Room';
import RoomSearch from './pages/RoomSearch';
import NotFound from './pages/NotFound';

function App() {
  return (
    <div>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/main" element={<Main />} />
        <Route path="/room" element={<Room />} />
        <Route path="/roomsearch" element={<RoomSearch />} />
        <Route path="/*" element={<NotFound />} />
      </Routes>
    </div>
  );
}

export default App;
