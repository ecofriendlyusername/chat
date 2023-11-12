import React from 'react';

interface Meeting {
  roomName: String;
  roomImage: string;
}

export default function Meeting({ roomName, roomImage }: Meeting) {
  return (
    <div className="relative px-2 py-4">
      <img
        src={roomImage}
        alt="모임 사진"
        className="rounded-full opacity-50"
      />
      <p className="absolute text-[100%] text-center text-white transform -translate-x-1/2 -translate-y-1/2 top-1/2 left-1/2 font-CookieRun_Regular">
        {roomName}
      </p>
    </div>
  );
}
