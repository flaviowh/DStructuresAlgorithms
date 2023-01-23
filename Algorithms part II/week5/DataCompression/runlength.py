
def run_length_encode(data):
    # Initialize an empty list to store the encoded data
    encoded_data = []
    # Keep track of the current character and the count of its repetitions
    current_char = None
    current_count = 0
    # Iterate over the input data
    for char in data:
        # If the current character is different from the previous one
        if char != current_char:
            # If there was a previous character, add it to the encoded data
            if current_char is not None:
                encoded_data.append((current_char, current_count))
            # Update the current character and reset the count
            current_char = char
            current_count = 1
        else:
            # If the current character is the same as the previous one, increment the count
            current_count += 1
    # Add the last character and count to the encoded data
    encoded_data.append((current_char, current_count))
    return encoded_data

# Test the function

data = "aaaabbbcccddeeff"
encoded_data = run_length_encode(data)
print(encoded_data)
# Output: [('a', 4), ('b', 3), ('c', 3), ('d', 2), ('e', 2), ('f', 2)]
# for test input "aaaabbbcccddeeff"
