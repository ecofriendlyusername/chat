/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/**/*.{html,js,ts,tsx}'],
  theme: {
    fontFamily: {
      CookieRun_Regular: ['CookieRun'],
      CookieRun_Bold: ['CookieRun_Bold'],
      CookieRun_Black: ['CookieRun_Black'],
    },
    extend: {
      colors: {
        'brand-beige': '#ECDCD3',
        'brand-pink': '#F4B2B2',
      },
    },
  },

  plugins: [],
};
