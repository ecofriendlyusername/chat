import React, { useEffect, useState } from 'react';
import { Cookies } from 'react-cookie';

export default function Main() {
  const cookies = new Cookies();

  const check = (): void => {
    const jsessionid = cookies.get('JSESSIONID');
    console.log(jsessionid);
  }
  return (
    <div>
      {cookies.get('JSESSIONID') ? (
        <h1>main 페이지</h1>
      ) : (
        <div>
          <a href="http://localhost:8081/oauth2/authorization/google">
            click here
          </a>
        </div>
      )}
      <button onClick={() => check()}>쿠키 확인</button>
    </div>
  )
}
