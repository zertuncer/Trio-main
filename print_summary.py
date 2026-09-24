import json

with open(r'c:\Users\ZER\Desktop\Trio-main\swift_views_inventory.json', 'r', encoding='utf-8') as f:
    items = json.load(f)

by_module = {}
for it in items:
    m = it['module']
    if m not in by_module:
        by_module[m] = []
    by_module[m].append(it)

print(f"Total modules: {len(by_module)}")
for m, files in sorted(by_module.items(), key=lambda x: -len(x[1])):
    total_lines = sum(x['lines'] for x in files)
    print(f"\n==========================================")
    print(f"MODULE: {m} ({len(files)} files, {total_lines} lines)")
    print(f"==========================================")
    for f in sorted(files, key=lambda x: x['fileName']):
        v_str = ', '.join(f['views']) if f['views'] else '(modifiers/subviews)'
        print(f"  - {f['fileName']} ({f['lines']} satır) -> Views: [{v_str}]")
        if f['dependencies']:
            print(f"      Bağımlılıklar: {f['dependencies']}")
