import React from 'react';
import Navibar from '../../components/Nav';
import SideMeetingList from './SideMeetingList';
import MeetingRoom from './MeetingRoom';

export default function Room() {
  return (
    <div>
      <Navibar />
      <div className="flex">
        <SideMeetingList />
        <MeetingRoom />
      </div>
    </div>
  );
}
