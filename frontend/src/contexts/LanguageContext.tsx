import React, { createContext, useContext, useState, ReactNode } from "react";
import en from "@/locales/messages_en.json";
import hi from "@/locales/messages_hi.json";
import mr from "@/locales/messages_mr.json";

type Messages = Record<string, string>;
type LangCode = "en" | "hi" | "mr";

const translations: Record<LangCode, Messages> = { en, hi, mr };

interface LanguageContextProps {
  language: LangCode;
  setLanguage: (lang: LangCode) => void;
  t: (key: string) => string;
}

const LanguageContext = createContext<LanguageContextProps | undefined>(undefined);

export const LanguageProvider = ({ children }: { children: ReactNode }) => {
  const [language, setLanguage] = useState<LangCode>("en");

  const t = (key: string) => translations[language][key] || key;

  return (
    <LanguageContext.Provider value={{ language, setLanguage, t }}>
      {children}
    </LanguageContext.Provider>
  );
};

export const useLanguage = () => {
  const context = useContext(LanguageContext);
  if (!context) throw new Error("useLanguage must be used within LanguageProvider");
  return context;
};
