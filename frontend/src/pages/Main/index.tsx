import React, { useEffect, useState } from 'react';
import { useCookies } from 'react-cookie';

export default function Main() {
  const [cookies, setCookie] = useCookies(['name']);
  useEffect (() => {
    console.log(cookies)
    
  },[])

  if (cookies.name === null)
    return (
      <div>
        <a href="http://localhost:8081/oauth2/authorization/google">
          click here
        </a>
      </div>
    );

  return (
    <div>
      {cookies.name}
      <h1>main페이지</h1>
    </div>
  );
}
