import SwiftUI
import Combine
import common

struct ContentView: View {
    var genders = ["Male", "Female"]
    @State var ageInput: String = ""
    @State var selectedGender: String = "Male"
    @State var personNumber: String = ""

    var body: some View {
        VStack(alignment: .leading) {
            TextField("Enter age", text: $ageInput).keyboardType(.numberPad)

            Picker("Please choose a color", selection: $selectedGender) {
                ForEach(genders, id: \.self) {
                    Text($0)
                }
            }

            Button("Calculate") {
                guard let age = Int(ageInput) else {
                    return
                }

                guard let gender = getGender(genderInput: selectedGender) else {
                    return
                }

                let personNumberRandomizer = PersonNumberRandomizer()
                personNumber = personNumberRandomizer.randomizePersonNumber(age: Int32(age), gender: gender)
            }

            Text("Personnumber: \(personNumber)")
        }
    }
}

func getGender(genderInput: String) -> PersonNumberRandomizer.Gender? {
    switch genderInput {
    case "Male":
        return PersonNumberRandomizer.Gender.male
    case "Female":
        return PersonNumberRandomizer.Gender.female
    default:
        return nil
    }
}


struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
