import { open } from 'fs';
import React, { useCallback, useState, useEffect } from 'react';

interface ModalState {
  isOpen: boolean;
  open: () => void;
  close: () => void;
}

const useModal = (): ModalState => {
  const [isOpen, setIsOpen] = useState<boolean>(false);

  /*모달 열기 */
  const open = useCallback(() => {
    setIsOpen(true);
  }, [isOpen]);

  /*모달 닫히기 */
  const close = useCallback(() => {
    setIsOpen(false);
  }, [isOpen]);

  return {
    isOpen,
    open,
    close,
  };
};
export default useModal;
