import React from 'react';

interface Meeting {
  roomName: String;
  roomImage: string;
}

export default function Meeting({ roomName, roomImage }: Meeting) {
  return (
    <div className="relative">
      <img
        src={roomImage}
        alt="모임 사진"
        className="w-[22vw] h-[25vh] mx-auto mt-5 rounded"
      />
      <div className="bg-black w-[22vw] h-[25vh] absolute inset-0 mx-auto mt-5 opacity-60 rounded"></div>
      <p
        className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2
      font-CookieRun_Regular
      text-2xl
      text-center
      text-white"
      >
        {roomName}
      </p>
    </div>
  );
}
