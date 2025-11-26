// src/utils/history.ts

export type ViewedLaw = {
  lawId: string;
  title: string;
};

export type ViewedSituation = {
  id: string;
  title: string;
};

const STORAGE_KEY_LAWS = "viewed_laws";
const STORAGE_KEY_SITUATIONS = "viewed_situations";
const MAX_ITEMS = 10;

export function getViewedLaws(): ViewedLaw[] {
  try {
    const json = localStorage.getItem(STORAGE_KEY_LAWS);
    if (!json) return [];

    const list = JSON.parse(json);
    if (!Array.isArray(list)) return [];

    return list;
  } catch {
    return [];
  }
}

export function saveViewedLaw(newLaw: ViewedLaw) {
  try {
    let list = getViewedLaws();
    list = list.filter((x) => x.lawId !== newLaw.lawId);
    list.unshift(newLaw);
    if (list.length > MAX_ITEMS) list = list.slice(0, MAX_ITEMS);
    localStorage.setItem(STORAGE_KEY_LAWS, JSON.stringify(list));
  } catch {}
}

export function getViewedSituations(): ViewedSituation[] {
  try {
    const json = localStorage.getItem(STORAGE_KEY_SITUATIONS);
    if (!json) return [];
    const list = JSON.parse(json);
    if (!Array.isArray(list)) return [];
    return list;
  } catch {
    return [];
  }
}

export function saveViewedSituation(newSituation: ViewedSituation) {
  try {
    let list = getViewedSituations();
    list = list.filter((x) => x.id !== newSituation.id);
    list.unshift(newSituation);
    if (list.length > MAX_ITEMS) list = list.slice(0, MAX_ITEMS);
    localStorage.setItem(
      STORAGE_KEY_SITUATIONS,
      JSON.stringify(list)
    );
  } catch {}
}
