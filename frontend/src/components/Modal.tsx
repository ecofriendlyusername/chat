import React, { useRef, useState } from 'react';
import styles from './Modal.module.css';
import { AiOutlineClose } from 'react-icons/ai';
import { BsCameraFill } from 'react-icons/bs';
import { AiOutlinePlusCircle } from 'react-icons/ai';
import { useSubmit } from 'react-router-dom';

interface ModalProps {
  close: () => void;
}

export default function Modal({ close }: ModalProps) {
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
            size={50}
            className="flex ml-auto mr-5 mt-5"
            onClick={close}
          />
          <div className="flex-col flex items-center justify-center">
            <p className="font-CookieRun_Regular text-2xl">룸 만들기</p>
            <input
              placeholder="룸이름을 입력하세요"
              style={{ width: '15vw', height: '4vh', borderRadius: '10px' }}
              className="font-CookieRun_Regular flex-center mt-4"
            />
            <label
              htmlFor="input-file"
              style={{
                background: '#F0F1F4',
                width: '15vw',
                height: '25vh',
                marginTop: '15px',
                zIndex: 1,
              }}
              className="flex-col flex items-center justify-center"
            >
              <BsCameraFill size="100" style={{ color: '#AB8E8E' }} />
              <input
                type="file"
                id="input-file"
                accept=".png, .jpg, image"
                style={{
                  display: 'none',
                  width: '0',
                  height: '0',
                  overflow: 'hidden',
                }}
              />
              <p className="font-CookieRun_Regular">사진을 넣어주세요</p>
            </label>
            <div className="flex mt-4">
              <input
                placeholder="초대할 이메일 입력"
                style={{ width: '12vw', height: '4vh', borderRadius: '10px' }}
                className="font-CookieRun_Regular flex-center "
              />
              <AiOutlinePlusCircle
                size={40}
                style={{ color: '#AB8E8E', marginLeft: '10px' }}
              />
            </div>
          </div>
          <div className="flex items-center justify-center mt-[5vh]">
            {isPublic ? (
              <div
                className="font-CookieRun_Regular w-[3vw] h-[4vh] flex items-center justify-center ml-auto mr-3 rounded-md"
                style={{ background: '#E7CFC4' }}
                onClick={() => setIsPublic(false)}
              >
                공개
              </div>
            ) : (
              <div
                className="font-CookieRun_Regular w-[3vw] h-[4vh] flex items-center justify-center ml-auto mr-3 rounded-md"
                style={{ background: '#F0F1F4' }}
                onClick={() => setIsPublic(true)}
              >
                비공개
              </div>
            )}

            <div
              className="font-CookieRun_Regular w-[10vw] h-[5vh] flex items-center justify-center mr-[7vw] rounded-md"
              style={{ background: '#E7CFC4' }}
            >
              룸생성
            </div>
          </div>
        </div>
      </div>
      ;
    </>
  );
}
