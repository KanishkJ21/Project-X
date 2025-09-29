import { FarmerCard } from "@/components/ui/farmer-card";
import { BottomNav } from "@/components/navigation/bottom-nav";
import {
  Cloud,
  TrendingUp,
  MessageCircle,
  BookOpen,
  User,
  Droplets,
  Languages,
  LogOut,
} from "lucide-react";
import { useNavigate } from "react-router-dom";
import { useLanguage } from "@/contexts/LanguageContext";

export default function Home() {
  const navigate = useNavigate();
  const { t } = useLanguage();

  const quickActions = [
    { title: t("home.quickActions.weather"), icon: Cloud, path: "/weather", variant: "primary" as const },
    { title: t("home.quickActions.recommendation"), icon: BookOpen, path: "/recommendation", variant: "secondary" as const },
    { title: t("home.quickActions.market"), icon: TrendingUp, path: "/market", variant: "default" as const },
    { title: t("home.quickActions.support"), icon: MessageCircle, path: "/support", variant: "default" as const },
    { title: t("home.quickActions.soil"), icon: Droplets, path: "/soil", variant: "default" as const },
    { title: t("home.quickActions.language"), icon: Languages, path: "/language", variant: "default" as const },
  ];

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-background to-muted pb-20">
      {/* Header */}
      <div className="gradient-primary text-primary-foreground p-6">
        <div className="flex items-center justify-between">
          <div>
            <h1 className="text-2xl font-bold">{t("home.welcome")}</h1>
            <p className="text-primary-foreground/80">{t("home.subtitle")}</p>
          </div>
          <div className="flex items-center gap-4">
            <User size={32} />
            <button
              onClick={handleLogout}
              className="flex items-center gap-1 text-red-600 hover:text-red-700"
            >
              <LogOut size={20} />
              {t("home.logout")}
            </button>
          </div>
        </div>
      </div>

      {/* Quick Actions */}
      <div className="p-6 space-y-6">
        <h2 className="text-xl font-bold text-foreground">{t("home.quickActions.title")}</h2>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          {quickActions.map((action) => (
            <FarmerCard
              key={action.path}
              title={action.title}
              icon={action.icon}
              variant={action.variant}
              onClick={() => navigate(action.path)}
              className="h-36"
            />
          ))}
        </div>

        {/* Today's Summary */}
        <div className="bg-card rounded-2xl p-6 border shadow-lg">
          <h3 className="font-semibold text-card-foreground mb-4 text-lg">{t("home.todaysSummary.title")}</h3>
          <div className="grid grid-cols-3 gap-6 text-center">
            <div className="bg-muted rounded-xl p-4 shadow-sm">
              <p className="text-3xl font-bold text-primary">28°C</p>
              <p className="text-sm text-muted-foreground">{t("home.todaysSummary.temperature")}</p>
            </div>
            <div className="bg-muted rounded-xl p-4 shadow-sm">
              <p className="text-3xl font-bold text-secondary">₹45/kg</p>
              <p className="text-sm text-muted-foreground">{t("home.todaysSummary.wheatPrice")}</p>
            </div>
            <div className="bg-muted rounded-xl p-4 shadow-sm">
              <p className="text-3xl font-bold text-success">Good</p>
              <p className="text-sm text-muted-foreground">{t("home.todaysSummary.weather")}</p>
            </div>
          </div>
        </div>
      </div>

      <BottomNav />
    </div>
  );
}
