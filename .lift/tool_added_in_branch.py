#!/usr/bin/env python3

import argparse
import json
import os
from pathlib import Path

def emit_version():
    print(1)


def emit_name():
    print("tool_added_in_branch")


def emit_applicable():
    print("true")

def run(path):
    pathlist = Path(path).glob('**/*.md')

    tool_notes = []
    for filename in pathlist:
        tool_notes.extend(process_file(filename))

    print(json.dumps(tool_notes))

def process_file(filename):
    file_display = str(filename)
    tool_notes = []
    with open(filename, 'r') as f:
        current_line = 0
        for line in f:
            current_line += 1
            if("branch tool" in line):
                tool_notes.append(line_to_tool_note(file_display, current_line, "Did you mean an axe?"))

    return tool_notes

def line_to_tool_note(filename, line_number, message):
    return {
        "type": "Tool Added In Branch",
        "message": message,
        "file": filename,
        "line": line_number
    }

def main():
    parser = argparse.ArgumentParser(description='Tool Added In Branch')
    parser.add_argument('path', metavar='PATH', help='Path to code')
    parser.add_argument('commit_hash', metavar='HASH', help='Commit hash')
    parser.add_argument('command', metavar='COMMAND', help='Command')

    args = parser.parse_args()

    path = args.path

    command = args.command

    if command == "version":
        emit_version()
    elif command == "name":
        emit_name()
    elif command == "applicable":
        emit_applicable()
    elif command == "run":
        run(path)

if __name__ == "__main__":
    main()
