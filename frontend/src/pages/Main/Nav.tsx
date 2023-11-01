import React from 'react';
import Profile from '../../Images/profile.png';

export default function Nav() {
  return (
    <div className="h-[10vh] w-[100vw] bg-brand-pink">
      <div className="flex">
        <img src={Profile} alt="프로필 사진" className="w-[6rem] ml-5" />
        <img src={Profile} alt="프로필 사진" className="w-[6rem] ml-5" />
        <img src={Profile} alt="프로필 사진" className="w-[6rem] ml-5" />
        <img src={Profile} alt="프로필 사진" className="w-[6rem] ml-5" />
        <img src={Profile} alt="프로필 사진" className="w-[6rem] ml-5" />
        <img src={Profile} alt="프로필 사진" className="w-[6rem] ml-5" />
      </div>
      <div></div>
    </div>
  );
}
