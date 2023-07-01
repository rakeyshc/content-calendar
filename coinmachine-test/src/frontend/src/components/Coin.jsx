import React from 'react';

const Coin = ({ value, count }) => {
  return (
    <div className='bg-slate-200 text-blue-700 font-semibold'>
      {value}: {count}
    </div>
  );
}

export default Coin;