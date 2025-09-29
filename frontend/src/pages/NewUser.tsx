import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { FarmerButton } from "@/components/ui/farmer-button";
import { FarmerInput } from "@/components/ui/farmer-input";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { useLanguage } from "@/contexts/LanguageContext";

export default function NewUser() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();
  const { t } = useLanguage();

  const handleRegister = async () => {
    if (!username) return setError(t("login.enterUsername"));
    if (!password) return setError(t("login.enterPassword"));

    try {
      const res = await fetch("http://localhost:8081/api/users/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password, email }),
      });
      const data = await res.json();

      if (data.id) {
        alert(t("login.userCreated"));
        navigate("/login");
      } else {
        setError(data.message || t("login.registerError"));
      }
    } catch {
      setError(t("login.registerError"));
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-primary-light to-secondary-light p-4 flex items-center justify-center">
      <div className="w-full max-w-md space-y-6">
        <Card className="border-2">
          <CardHeader className="text-center">
            <CardTitle className="text-2xl">{t("login.registerTitle")}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-6">
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
            <FarmerInput
              label={`${t("login.emailLabel")} `}
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />

            {error && <p className="text-red-600">{error}</p>}

            <FarmerButton
              variant="primary"
              size="xl"
              className="w-full"
              onClick={handleRegister}
            >
              {t("login.registerButton")}
            </FarmerButton>

            <FarmerButton
              variant="secondary"
              size="xl"
              className="w-full mt-2"
              onClick={() => navigate("/")}
            >
              {t("login.backToLogin")}
            </FarmerButton>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
