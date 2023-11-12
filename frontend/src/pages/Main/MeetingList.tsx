import React, { useState } from 'react';
import room1 from '../../Images/room1.jpg';
import Meeting from './Meeting';
import { AiOutlinePlus } from 'react-icons/ai';
import Modal from '../../components/Modal';
import useModal from '../../hooks/modals';

export default function MeetingList() {
  interface Meeting {
    roomName: String;
    roomImage: string;
  }
  const { isOpen, open, close } = useModal();

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
    <div className="grid grid-cols-4">
      {Meetings.map((meeting, idx) => (
        <Meeting
          key={idx}
          roomName={meeting.roomName}
          roomImage={meeting.roomImage}
        />
      ))}
      <div className="bg-brand-black w-[22vw] h-[25vh] mx-auto mt-5 flex items-center justify-center rounded">
        <AiOutlinePlus size={100} color="white" onClick={open} />
        {isOpen && <Modal close={close} />}
      </div>
    </div>
  );
}
