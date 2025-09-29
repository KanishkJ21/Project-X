import React from "react";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { useNavigate } from "react-router-dom";
import { Card, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { useLanguage } from "@/contexts/LanguageContext";
import { BottomNav } from "@/components/navigation/bottom-nav";

interface User {
  id: string;
  username: string;
  email: string;
  role: string;
  approved?: boolean;
}

const Dashboard = () => {
  const { t } = useLanguage();
  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const { data: currentUser, isLoading } = useQuery({
    queryKey: ["currentUser"],
    queryFn: async () => {
      const res = await fetch("http://localhost:8081/api/users/me", {
        headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
      });
      if (!res.ok) throw new Error(t("error.fetchUser"));
      return res.json();
    },
  });

  const { data: allUsers } = useQuery({
    queryKey: ["allUsers"],
    queryFn: async () => {
      const res = await fetch("http://localhost:8081/api/users/all", {
        headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
      });
      if (!res.ok) throw new Error(t("error.fetchUsers"));
      return res.json();
    },
    enabled: currentUser?.role === "ROLE_ADMIN",
  });

  const deleteMutation = useMutation({
    mutationFn: async (id: string) => {
      const res = await fetch(`http://localhost:8081/api/users/${id}`, {
        method: "DELETE",
        headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
      });
      if (!res.ok) throw new Error(t("error.deleteUser"));
      return res.json();
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["allUsers"] });
      queryClient.invalidateQueries({ queryKey: ["currentUser"] });
    },
  });

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/");
  };

  if (isLoading) return <p className="text-center mt-20">{t("loading")}...</p>;

  return (
    <div className="pb-20 px-4 bg-green-50 min-h-screen">
      <h1 className="text-3xl font-bold text-green-700 mb-6">{t("dashboard.title")}</h1>

      {/* KPI Cards */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4 mb-6">
        <Card className="bg-white border-l-4 border-green-500 shadow-md">
          <CardContent>
            <h2 className="text-lg font-semibold text-green-600">{t("dashboard.username")}</h2>
            <p className="text-gray-700">{currentUser.username}</p>
          </CardContent>
        </Card>

        <Card className="bg-white border-l-4 border-green-500 shadow-md">
          <CardContent>
            <h2 className="text-lg font-semibold text-green-600">{t("dashboard.email")}</h2>
            <p className="text-gray-700">{currentUser.email}</p>
          </CardContent>
        </Card>

        <Card className="bg-white border-l-4 border-green-500 shadow-md">
          <CardContent>
            <h2 className="text-lg font-semibold text-green-600">{t("dashboard.role")}</h2>
            <p className="text-gray-700">{currentUser.role}</p>
          </CardContent>
        </Card>
      </div>

      {/* Users Section */}
      {currentUser.role === "ROLE_ADMIN" && allUsers && (
        <div className="mb-6">
          <h2 className="text-2xl font-semibold text-green-700 mb-3">{t("dashboard.allUsers")}</h2>
          {allUsers.map((user: User) => (
            <Card key={user.id} className="mb-2 bg-white shadow hover:shadow-lg transition">
              <CardContent className="flex justify-between items-center">
                <div>
                  <p className="font-medium">{user.username}</p>
                  <p className="text-sm text-gray-600">{user.email} - {user.role}</p>
                </div>
                {user.id !== currentUser.id && (
                  <Button
                    className="bg-red-500 hover:bg-red-600 text-white"
                    onClick={() => deleteMutation.mutate(user.id)}
                  >
                    {t("dashboard.delete")}
                  </Button>
                )}
              </CardContent>
            </Card>
          ))}
        </div>
      )}

      {currentUser.role === "ROLE_FARMER" && (
        <Button
          className="bg-red-500 hover:bg-red-600 text-white mb-6"
          onClick={() => deleteMutation.mutate(currentUser.id)}
        >
          {t("dashboard.deleteMyAccount")}
        </Button>
      )}

      {/* Logout Button */}
      <Button className="bg-green-600 hover:bg-green-700 text-white w-full mb-6" onClick={handleLogout}>
        {t("dashboard.logout")}
      </Button>

      {/* Bottom Nav */}
      <BottomNav />
    </div>
  );
};

export default Dashboard;
