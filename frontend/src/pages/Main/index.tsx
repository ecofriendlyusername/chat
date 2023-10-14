import React, { useEffect, useState } from 'react';

export default function Main() {
  const [istoken, setIstoken] = useState(false);
  useEffect(() => {
    if (localStorage.getItem('accessToken')) {
      setIstoken(true);
    } else {
      setIstoken(false);
    }
  }, []);

  if (!istoken)
    return (
      <div>
        <a href="http://localhost:8081/oauth2/authorization/google">
          click here
        </a>
      </div>
    );

  return (
    <div>
      <h1>main페이지</h1>
    </div>
  );
}
