import { useState, useEffect } from "react";
import { fetchUserById } from "../middleware/users-data-api";
import { Avatar } from "@mui/material";

export default function UserProfile({ userId }) {
  const [user, setUser] = useState(null);

  useEffect(() => {
    const getUser = async () => {
      try {
        const data = await fetchUserById(userId);
        setUser(data);
      } catch (error) {
        console.error("Failed to fetch user:", error);
      }
    };

    getUser();
  }, []);
  return (
    <>
      {user && (
        <div>
          <p>{user.name}</p>
          <Avatar
            alt="user.name"
            src={user.avatar.imageUrl}
            sx={{ width: 75, height: 75 }}
            variant="rounded"
            style={
              user.isPremium && {
                border: "2px solid gold",
              }
            }
          />
        </div>
      )}
    </>
  );
}
