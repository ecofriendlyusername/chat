import React from 'react';

export default function MeetingRoom() {
  return (
    <div className="flex">
      <div className="w-[12vw] bg-[#E7CFC4]">
        <div className="py-[3vh] text-[2vh] text-center font-CookieRun_Bold">
          채팅방명
        </div>
        <div className="pb-[2vh] px-[1vw] font-CookieRun_Regular">
          <div>채팅채널</div>
          <div>채널 명 들어갈곳</div>
        </div>
        <div className="mb-[2vh] px-[1vw] font-CookieRun_Regular">
          <div>음성채널</div>
          <div>음성 채널 명 들어갈곳</div>
        </div>
      </div>
      <div className="w-[80vw] flex flex-col px-[1vw] py-[1vh]">
        <div className="h-[90%] bg-slate-300 p-[3%] font-CookieRun_Regular">
          채팅 내용
        </div>
        <input
          type="text"
          className="px-[2vw] h-[8%] bg-[#F0F1F4] rounded-3xl text-2xl font-CookieRun_Bold
        "
          placeholder="메시지를 입력하세요"
        />
      </div>
    </div>
  );
}
