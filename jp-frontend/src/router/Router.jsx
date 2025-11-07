import { BrowserRouter, Routes, Route } from "react-router-dom";
import SituationList from "../pages/SituationList";
import SituationDetail from "../pages/SituationDetail";

export default function Router() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<SituationList />} />
        <Route path="/detail/:id" element={<SituationDetail />} />
      </Routes>
    </BrowserRouter>
  );
}
