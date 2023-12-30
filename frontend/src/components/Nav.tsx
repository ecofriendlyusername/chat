import React from 'react';
import Profile from '../Images/profile.png';
import { BsPlusCircleFill } from 'react-icons/bs';
import FriendModal from '../components/FriendModal';
import useModal from '../hooks/modals';

export default function Nav() {
  interface User {
    name: string;
    src: string;
  }

  const { isOpen, open, close } = useModal();

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
          <div className="mx-6 " key={index}>
            <img src={user.src} className="h-[10vh]" alt="프로필 사진" />
            <p className="mt-1 text-center font-CookieRun_Regular">
              {user.name}
            </p>
          </div>
        ))}

        {/* BsPlusCircleFill 아이콘이 클릭되면 isOpen이 true가 되어 FriendModal이 렌더링됨 */}
        <BsPlusCircleFill
          size={90}
          className="mt-2 ml-4 opacity-70"
          onClick={open}
        />
        {isOpen && <FriendModal close={close} />}

        <div className="flex ml-auto">
          <div className="flex flex-col mr-10">
            <img src={Profile} className="h-[10vh]" alt="내 프로필" />
            <p className="mt-1 text-center font-CookieRun_Regular">나</p>
          </div>
        </div>
      </div>
    </div>
  );
}
