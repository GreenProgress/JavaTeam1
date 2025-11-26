import React from "react";
import styles from "./SituationCard.module.css";

export default function SituationCard({ title, summary, lawRef, onClick }) {
  return (
    <article className={styles.card} onClick={onClick}>
      <h3 className={styles.title}>{title}</h3>

      {summary && <p className={styles.summary}>{summary}</p>}

      {lawRef && (
        <p className={styles.law}>
          관련 법령: <span>{lawRef}</span>
        </p>
      )}
    </article>
  );
}
