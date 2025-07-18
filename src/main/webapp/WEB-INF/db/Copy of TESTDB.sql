SELECT S.NPP,
       C.NAME,
    --   C.SIGN,
     --  C.SAFETY_GROUP_ID,
      -- CC.FI,
       CT.*
FROM SCENARIOS AS S,
   --  CALCULATION_CRITERIA AS CC,
     CRITERIA AS C,
     CALCULATION_TABLE AS CT
WHERE
 C.ID = CT.CRITERIA_ID
  AND 
 S.ID = CT.SCENARIOS_ID
 -- AND CC.CALCULATION_ID = CT.CALCULATION_ID
--  AND CC.CRITERIA_ID = CT.CRITERIA_ID
  AND
  CT.CALCULATION_ID = 2
  AND CT.ENABLE = 1
ORDER BY CT.SCENARIOS_ID