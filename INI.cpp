#include <cstdlib> // Necesario para 'system'
#include <iostream>

int main() {
    std::cout << "Iniciando interfaz gráfica..." << std::endl;
    
    int status = system("java AutomataUI");

    if (status == 0) {
        std::cout << "El programa finalizó correctamente." << std::endl;
    } else {
        std::cout << "Ocurrió un error al ejecutar el script." << std::endl;
    }

    return 0;
}
