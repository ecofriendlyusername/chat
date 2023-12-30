import React, { useRef, useState } from 'react';
import styles from './RoomModal.module.css';
import { AiOutlineClose } from 'react-icons/ai';
import { BsCameraFill } from 'react-icons/bs';
import { AiOutlinePlusCircle } from 'react-icons/ai';
import axios from 'axios';

interface ModalProps {
  close: () => void;
}

export default function RoomModal({ close }: ModalProps) {
  const outside = useRef<HTMLDivElement>(null);
  const [isPublic, setIsPublic] = useState<boolean>(false); // 공개, 비공개

  const makeGatheringApi = async () => {
    console.log(111111);
    try {
      const fileInput = document.getElementById(
        'input-file',
      ) as HTMLInputElement;
      const gatheringTitle = (
        document.getElementById('input-title') as HTMLInputElement
      ).value;
      const emailList = [];
      emailList.push('cococo1622@gmail.com');
      emailList.push('coco1622555@gmail.com');
      const files = fileInput.files;
      if (files && files.length > 0) {
        const makeGatheringForm = new FormData();
        makeGatheringForm.append('gathering_image', files[0]);
        makeGatheringForm.append(
          'gathering',
          new Blob(
            [
              JSON.stringify({
                gatheringName: gatheringTitle,
                participants: emailList,
              }),
            ],
            { type: 'application/json' },
          ),
        );

        let res = await axios.post(
          'http://localhost:8081/gathering/makegatheringwithemails',
          makeGatheringForm,
          {
            headers: {
              'Content-Type': 'multipart/form-data',
            },
            withCredentials: true,
          },
        );
        return res;
      }
    } catch (err) {
      console.log('[ERROR] makeGatheringApi');
      throw err;
    }
  };

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
            className="flex mt-5 ml-auto mr-5"
            onClick={close}
          />
          <div className="flex flex-col items-center justify-center">
            <p className="text-2xl font-CookieRun_Regular">룸 만들기</p>
            <input
              id="input-title"
              placeholder="룸이름을 입력하세요"
              style={{ width: '15vw', height: '4vh', borderRadius: '10px' }}
              className="mt-4 font-CookieRun_Regular flex-center"
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
              className="flex flex-col items-center justify-center"
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

            <button
              className="font-CookieRun_Regular w-[10vw] h-[5vh] flex items-center justify-center mr-[7vw] rounded-md"
              style={{ background: '#E7CFC4' }}
              onClick={makeGatheringApi}
            >
              룸생성
            </button>
          </div>
        </div>
      </div>
      ;
    </>
  );
}
