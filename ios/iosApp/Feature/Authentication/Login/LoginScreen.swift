import SwiftUI
import Combine
import shared

struct LoginScreen_Previews: PreviewProvider {
    static var previews: some View {
        LoginScreen()
    }
}

struct LoginScreen: View {
    @StateObject
    var viewModel = LoginScreenViewModel(repository: LoginRepository())
    
    @State var username: String = ""
    @State var password: String = ""
    
    var body: some View {
        VStack(spacing: 0) {
            EmailTextField(value: username)
            PasswordTextField(value: password)
                .padding(.top, 16)
            SignInButton() {
                
            }
                .padding(.top, 16)
            SignUpButton() {
                
            }
                .padding(.top, 32)
        }
        .padding(.horizontal, 16)
    }
}

private struct EmailTextField: View {
    @State
    var value: String
    
    var body: some View {
        CommonTextField(
            value: value,
            leadingIcon: "Email",
            hint: "Email",
            isSecure: false
        )
    }
}

private struct PasswordTextField: View {
    @State
    var value: String
    
    var body: some View {
        CommonTextField(
            value: value,
            leadingIcon: "Password",
            hint: "Password",
            isSecure: true
        )
    }
}

private struct CommonTextField: View {
    
    @State
    var value: String
    let leadingIcon: String
    let hint: String
    let isSecure: Bool
    
    @FocusState private var focused: Bool
    
    var body: some View {
        HStack {
            Image(leadingIcon)
            if isSecure {
                createSeacure()
            } else {
                createNormal()
            }
        }
        .padding()
        .overlay(
            RoundedRectangle(cornerRadius: 8)
                .stroke(lineWidth: 1)
                .foregroundColor(focused ? AppColor.LightGreenBorder : AppColor.LightGreyBorder)
        )
        .background(AppColor.LightGrey)
    }
    
    private func createSeacure() -> some View {
        return SecureField("", text: $value)
            .focused($focused)
            .placeholder(when: value.isEmpty) {
                Text(hint)
                    .font(AppFont.body2)
                    .foregroundColor(AppColor.TextColor)
            }
            .font(AppFont.body2)
    }
    
    private func createNormal() -> some View {
        return TextField("", text: $value)
            .focused($focused)
            .placeholder(when: value.isEmpty) {
                Text(hint)
                    .font(AppFont.body2)
                    .foregroundColor(AppColor.TextColor)
            }
            .font(AppFont.body2)
    }
}

private extension View {
    func placeholder<Content: View>(
        when shouldShow: Bool,
        alignment: Alignment = .leading,
        @ViewBuilder placeholder: () -> Content) -> some View {
            
            ZStack(alignment: alignment) {
                placeholder().opacity(shouldShow ? 1 : 0)
                self
            }
        }
}

private struct SignInButton: View {
    
    let action: () -> Void

    var body: some View {
        
        Button(action: action, label: {
            Text("Sign in")
                .frame(maxWidth: .infinity)
                .font(AppFont.h3)
                .padding()
                .background(
                    RoundedRectangle(cornerRadius: 8)
                        .fill(AppColor.Green)
                )
                .foregroundColor(Color.white)
        })
    }
}

private struct SignUpButton: View {
    
    let action: () -> Void
    
    var body: some View {
        Button(action: action, label: {
            Text("Don’t have an account? **Sign Up**")
                .font(AppFont.body1)
                .padding()
                .foregroundColor(AppColor.Green)
        })
    }
}