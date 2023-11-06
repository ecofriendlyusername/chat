import React from 'react';
import Profile from '../../Images/profile.png';
import { BsPlusCircleFill } from 'react-icons/bs';

export default function Nav() {
  interface User {
    name: string;
    src: string;
  }
  const users: User[] = [
    { name: '가영', src: Profile },
    { name: '동민', src: Profile },
    { name: '혜승', src: Profile },
    { name: '쿠키', src: Profile },
  ];
  return (
    <div className="h-[13vh] w-[100vw] bg-brand-pink">
      <div className="flex">
        {users.map((user, index) => (
          <div className=" mx-6" key={index}>
            <img src={user.src} className="h-[10vh]" alt="프로필 사진" />
            <p className="text-center mt-1 font-CookieRun_Regular">
              {user.name}
            </p>
          </div>
        ))}
        <BsPlusCircleFill size={90} className="opacity-70 ml-4 mt-2" />
        <div className="flex ml-auto">
          <div className="flex mr-10 flex-col">
            <img src={Profile} className="h-[10vh]" alt="내 프로필" />
            <p className="text-center mt-1 font-CookieRun_Regular">나</p>
          </div>
        </div>
      </div>
    </div>
  );
}
