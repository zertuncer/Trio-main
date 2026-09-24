import sqlite3, json

for cid in ['608d1108-0525-4e69-a490-27d9a6374c3a', '6fdc1ad6-aac4-4819-8e68-594541b2a34f']:
    db = rf'C:\Users\ZER\.gemini\antigravity-ide\conversations\{cid}.db'
    conn = sqlite3.connect(db)
    cols = [c[1] for c in conn.execute("PRAGMA table_info(steps);").fetchall()]
    print(f'Columns for {cid}: {cols}')
    row = conn.execute(f"SELECT {', '.join(cols)} FROM steps LIMIT 1;").fetchone()
    print('Sample row keys/types:', [(cols[i], type(row[i]), str(row[i])[:60]) for i in range(len(cols))])
