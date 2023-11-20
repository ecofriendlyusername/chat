import { useRef, useState } from 'react';
import styles from './FriendModal.module.css';
import { AiOutlineClose } from 'react-icons/ai';
import { BsCameraFill } from 'react-icons/bs';
import { AiOutlinePlusCircle } from 'react-icons/ai';

interface ModalProps {
  close: () => void;
}

export default function FriendModal({ close }: ModalProps) {
  const outside = useRef<HTMLDivElement>(null);
  const [isPublic, setIsPublic] = useState<boolean>(false); // 공개, 비공개
  return (
    <>
      <div
        className={styles.container}
        ref={outside}
        onClick={(e) => {
          if (e.target === outside.current) {
            close();
          }
        }}
      >
        <div className={styles.modal}>
          <AiOutlineClose
            size={30}
            className="flex ml-auto mr-5 mt-5"
            onClick={close}
          />
          <div className="flex-col flex items-center justify-center">
            <p className="font-CookieRun_Regular text-2xl">친구 추가</p>
            <input
              placeholder="친구의 이메일을 입력하세요"
              style={{ width: '15vw', height: '5vh', borderRadius: '10px' }}
              className="font-CookieRun_Regular flex-center mt-4"
            />
            <div
              className="font-CookieRun_Regular w-[10vw] h-[5vh] flex items-center justify-center rounded-md mt-[1rem]"
              style={{ background: '#E7CFC4' }}
            >
              초대하기
            </div>
          </div>
        </div>
      </div>
      ;
    </>
  );
}
