import os, re, json

base_dir = r"c:\Users\ZER\Desktop\Trio-main\Trio-main\Trio\Sources"
items = []

for root, dirs, files in os.walk(base_dir):
    for f in files:
        if f.endswith('.swift'):
            path = os.path.join(root, f)
            try:
                with open(path, 'r', encoding='utf-8', errors='ignore') as fp:
                    lines = fp.readlines()
                    content = ''.join(lines)
            except Exception:
                continue

            rel_path = os.path.relpath(path, base_dir).replace('\\', '/')
            
            # Check if this file is a View
            is_view = False
            if ': View' in content or ':View' in content or ': BaseView' in content or ':BaseView' in content:
                is_view = True
            elif '/View/' in rel_path or rel_path.startswith('Views/'):
                is_view = True
            elif 'ViewModifier' in content:
                is_view = True

            if is_view:
                parts = rel_path.split('/')
                module = 'General/Shared'
                if parts[0] == 'Modules' and len(parts) > 1:
                    module = parts[1]
                elif parts[0] == 'Views':
                    module = 'Shared Views (Components)'
                elif parts[0] == 'Application':
                    module = 'App Root'
                elif parts[0] == 'Router':
                    module = 'Navigation'
                elif parts[0] == 'Shortcuts':
                    module = 'Shortcuts'
                elif parts[0] == 'Services':
                    module = 'Services / UI Widgets'
                else:
                    module = parts[0]

                # Find views/structs
                views = re.findall(r'struct\s+([A-Za-z0-9_]+)\s*:[^{]*(?:\bView\b|\bBaseView\b)', content)
                if not views:
                    if 'extension' in content and 'RootView' in content:
                        views = ['RootView']
                    elif 'ViewModifier' in content:
                        views = re.findall(r'struct\s+([A-Za-z0-9_]+)\s*:[^{]*\bViewModifier\b', content)

                # Find dependencies
                deps = set()
                for m in re.finditer(r'@(StateObject|ObservedObject|EnvironmentObject|Injected|Environment)\s+(?:var|let)\s+([A-Za-z0-9_]+)\s*:\s*([A-Za-z0-9_<>?, .]+)', content):
                    type_clean = m.group(3).split('=')[0].strip()
                    deps.add(f"@{m.group(1)} {m.group(2)}: {type_clean}")
                for m in re.finditer(r'(?:var|let)\s+(state[A-Za-z0-9_]*|viewModel|resolver)\s*:\s*([A-Za-z0-9_<>?, .]+)', content, re.IGNORECASE):
                    type_clean = m.group(2).split('=')[0].strip()
                    deps.add(f"{m.group(1)}: {type_clean}")

                items.append({
                    'fileName': f,
                    'relPath': rel_path,
                    'module': module,
                    'lines': len(lines),
                    'views': list(views),
                    'deps': list(deps)
                })

with open(r"c:\Users\ZER\Desktop\Trio-main\views_full_inventory.json", 'w', encoding='utf-8') as out:
    json.dump(items, out, indent=2, ensure_ascii=False)

print(f"Scan complete. Total files: {len(items)}")
