import React from 'react';
import room1 from '../../Images/room1.jpg';
import Meeting from './Meeting';

export default function SideMeetingList() {
  interface Meeting {
    roomName: String;
    roomImage: string;
  }

  const Meetings: Meeting[] = [
    {
      roomName: '기다리고 있다',
      roomImage: room1,
    },
    {
      roomName: '어서와',
      roomImage: room1,
    },
    {
      roomName: '멋진 개발자들의 모임',
      roomImage: room1,
    },
    {
      roomName: '멋있는 사람들',
      roomImage: room1,
    },
  ];

  return (
    <div className="flex flex-col items-center h-[87vh] w-[8vw] bg-sidebar-beige">
      {Meetings.map((meeting, idx) => (
        <Meeting
          key={idx}
          roomName={meeting.roomName}
          roomImage={meeting.roomImage}
        />
      ))}
    </div>
  );
}
