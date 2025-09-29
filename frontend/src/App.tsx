import { Toaster } from "@/components/ui/toaster";
import { Toaster as Sonner } from "@/components/ui/sonner";
import { TooltipProvider } from "@/components/ui/tooltip";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { LanguageProvider } from "@/contexts/LanguageContext";


// Pages
import Login from "./pages/Login";
import NewUser from "./pages/NewUser";
import Home from "./pages/Home";
import Weather from "./pages/Weather";   // 👈 Make sure filename is `Weather.tsx`
import CropRecommendation from "./pages/CropRecommendation";
import Market from "./pages/Market";
import Support from "./pages/Support";
import NotFound from "./pages/NotFound";
import Dashboard from "./pages/Dashboard";



import SoilHealth from "./pages/SoilHealth"
import LanguageSettings from "./pages/LanguageSettings";  // 👈 import the page

// ... inside <Routes>




const queryClient = new QueryClient();

export default function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <LanguageProvider>
        <TooltipProvider>
          <Toaster />
          <Sonner />
          <BrowserRouter>
            <Routes>
              <Route path="/" element={<Login />} />
              <Route path="/new" element={<NewUser />} />
              <Route path="/home" element={<Home />} />
              <Route path="/weather" element={<Weather />} />
              <Route path="/recommendation" element={<CropRecommendation />} />
              <Route path="/market" element={<Market />} />
              <Route path="/soil" element={<SoilHealth/>}/>
              <Route path="/support" element={<Support />} />
              <Route path="/language" element={<LanguageSettings />} />
              <Route path="/dashboard" element={<Dashboard />} />
              {/* catch-all */}
              <Route path="*" element={<NotFound />} />
            </Routes>
          </BrowserRouter>
        </TooltipProvider>
      </LanguageProvider>
    </QueryClientProvider>
  );
}
