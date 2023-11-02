import React, { useRef } from 'react';
import styles from './Modal.module.css';
import { AiOutlineClose } from 'react-icons/ai';

interface ModalProps {
  close: () => void;
}

export default function Modal({ close }: ModalProps) {
  const outside = useRef<HTMLDivElement>(null);
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
            size={50}
            className="flex ml-auto mr-5 mt-5"
            onClick={close}
          />
          <div className="flex-col flex items-center justify-center">
            <p className="font-CookieRun_Regular text-2xl">룸 만들기</p>
            <input
              placeholder="룸이름을 입력하세요"
              style={{ height: '35px', borderRadius: '10px' }}
              className="font-CookieRun_Regular flex-center mt-3"
            />
            <div
              style={{ color: '#AB8E8E', width: '100px', height: '100px' }}
            ></div>
          </div>
        </div>
      </div>
      ;
    </>
  );
}
