import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { FarmerButton } from "@/components/ui/farmer-button";
import { FarmerInput } from "@/components/ui/farmer-input";
import { LanguageSelector } from "@/components/language-selector";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Leaf } from "lucide-react";
import { useLanguage } from "@/contexts/LanguageContext";

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();
  const { language, setLanguage, t } = useLanguage();

  const handleLogin = async () => {
    if (!username) return setError(t("login.enterUsername"));
    if (!password) return setError(t("login.enterPassword"));

    try {
      const res = await fetch("http://localhost:8081/api/users/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password }),
      });
      const data = await res.json();

      if (data.token) {
        // Store token and user info in localStorage
        localStorage.setItem("token", data.token);
        localStorage.setItem(
          "user",
          JSON.stringify({
            username: data.username || username,
            email: data.email || "",
            role: data.role || "ROLE_FARMER",
            id: data.id || "",
          })
        );
        // Redirect to Home page after login
        navigate("/home");
      } else {
        setError(data.message || t("login.invalidCredentials"));
      }
    } catch (err) {
      setError(t("login.invalidCredentials"));
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-primary-light to-secondary-light p-4 flex items-center justify-center">
      <div className="w-full max-w-md space-y-6">
        <div className="flex justify-end">
          <LanguageSelector value={language} onValueChange={setLanguage} />
        </div>

        <div className="text-center space-y-4">
          <div className="inline-flex items-center justify-center w-20 h-20 bg-primary rounded-full">
            <Leaf size={40} className="text-primary-foreground" />
          </div>
          <h1 className="text-3xl font-bold text-primary">{t("app.title")}</h1>
          <p className="text-lg text-muted-foreground">{t("login.subtitle")}</p>
        </div>

        <Card className="border-2">
          <CardHeader className="text-center">
            <CardTitle className="text-2xl">{t("login.title")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <FarmerInput
              label={t("login.usernameLabel")}
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
            <FarmerInput
              label={t("login.passwordLabel")}
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />

            {error && <p className="text-red-600 text-sm">{error}</p>}

            <FarmerButton
              variant="primary"
              size="xl"
              className="w-full mt-2"
              onClick={handleLogin}
            >
              {t("login.loginButton")}
            </FarmerButton>

            <FarmerButton
              variant="secondary"
              size="xl"
              className="w-full mt-2"
              onClick={() => navigate("/new")}
            >
              {t("login.createNewUser")}
            </FarmerButton>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
